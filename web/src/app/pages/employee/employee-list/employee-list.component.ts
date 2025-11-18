import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { EmployeeService } from '../../../core/services/employee.service';
import { Employee } from '../../../core/models/employee.model';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    ButtonModule,
    CardModule,
    TagModule,
    ConfirmDialogModule,
    ToastModule
  ],
  providers: [ConfirmationService, MessageService],
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.scss']
})
export class EmployeeListComponent implements OnInit {
  employees: Employee[] = [];
  loading = false;

  constructor(
    private employeeService: EmployeeService,
    private router: Router,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.messageService.clear();
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.loading = true;
    this.cdr.detectChanges();
    this.employeeService.getAllEmployees().subscribe({
      next: (data) => {
        this.employees = data;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        if (error.status !== 401) {
          console.error('Erro ao carregar funcionários', error);
          this.messageService.add({
            severity: 'error',
            summary: 'Erro',
            detail: 'Erro ao carregar lista de funcionários'
          });
        }
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  navigateToCreate(): void {
    this.router.navigate(['/employee/new']);
  }

  navigateToEdit(id: string): void {
    this.router.navigate(['/employee/edit', id]);
  }

  confirmDelete(employee: Employee): void {
    this.confirmationService.confirm({
      message: `Tem certeza que deseja excluir o funcionário ${employee.firstName} ${employee.lastName}?`,
      header: 'Confirmar Exclusão',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      accept: () => {
        this.deleteEmployee(employee.id);
      }
    });
  }

  deleteEmployee(id: string): void {
    this.employeeService.deleteEmployee(id).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Sucesso',
          detail: 'Funcionário excluído com sucesso'
        });
        this.loadEmployees();
      },
      error: (error) => {
        console.error('Erro ao excluir funcionário', error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Erro ao excluir funcionário'
        });
      }
    });
  }

  getStatusSeverity(enabled: boolean): 'success' | 'danger' {
    return enabled ? 'success' : 'danger';
  }

  getStatusLabel(enabled: boolean): string {
    return enabled ? 'Ativo' : 'Inativo';
  }

  getRolesSeverity(role: string): 'success' | 'info' | 'warn' | 'secondary' | 'contrast' | 'danger' {
    switch (role) {
      case 'ADMIN':
        return 'danger';
      case 'USER':
        return 'info';
      default:
        return 'secondary';
    }
  }
}
