import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { PasswordModule } from 'primeng/password';
import { CheckboxModule } from 'primeng/checkbox';
import { MultiSelectModule } from 'primeng/multiselect';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { EmployeeService } from '../../../core/services/employee.service';
import { CreateEmployeeRequest, UpdateEmployeeRequest, Employee } from '../../../core/models/employee.model';

@Component({
  selector: 'app-employee-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    CardModule,
    InputTextModule,
    ButtonModule,
    PasswordModule,
    CheckboxModule,
    MultiSelectModule,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './employee-form.component.html',
  styleUrls: ['./employee-form.component.scss']
})
export class EmployeeFormComponent implements OnInit {
  employeeForm!: FormGroup;
  isEditMode = false;
  employeeId: string | null = null;
  loading = false;
  submitting = false;

  availableRoles = [
    { label: 'Administrador', value: 'ADMIN' },
    { label: 'Usuário', value: 'USER' }
  ];

  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService,
    private router: Router,
    private route: ActivatedRoute,
    private messageService: MessageService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.checkEditMode();
  }

  initForm(): void {
    this.employeeForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.email]],
      firstName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      lastName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      roles: [['USER'], []],
      enabled: [true],
      emailVerified: [false]
    });
  }

  checkEditMode(): void {
    this.employeeId = this.route.snapshot.paramMap.get('id');
    if (this.employeeId) {
      this.isEditMode = true;
      this.employeeForm.get('username')?.disable();
      this.employeeForm.get('password')?.clearValidators();
      this.employeeForm.get('password')?.updateValueAndValidity();
      this.loadEmployee();
    }
  }

  loadEmployee(): void {
    if (!this.employeeId) return;

    this.loading = true;
    this.cdr.detectChanges();
    this.employeeService.getEmployeeById(this.employeeId).subscribe({
      next: (employee: Employee) => {
        this.employeeForm.patchValue({
          username: employee.username,
          email: employee.email,
          firstName: employee.firstName,
          lastName: employee.lastName,
          roles: employee.roles,
          enabled: employee.enabled,
          emailVerified: employee.emailVerified
        });
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Erro ao carregar dados do funcionário'
        });
        this.loading = false;
        this.cdr.detectChanges();
        this.navigateToList();
      }
    });
  }

  onSubmit(): void {
    if (this.employeeForm.invalid) {
      this.markFormGroupTouched(this.employeeForm);
      return;
    }

    this.submitting = true;

    if (this.isEditMode && this.employeeId) {
      this.updateEmployee();
    } else {
      this.createEmployee();
    }
  }

  createEmployee(): void {
    const request: CreateEmployeeRequest = this.employeeForm.value;

    this.employeeService.createEmployee(request).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Sucesso',
          detail: 'Funcionário criado com sucesso'
        });
        this.submitting = false;
        this.cdr.detectChanges();
        setTimeout(() => this.navigateToList(), 1500);
      },
      error: (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: error.error?.message
        });
        this.submitting = false;
        this.cdr.detectChanges();
      }
    });
  }

  updateEmployee(): void {
    if (!this.employeeId) return;

    const formValue = this.employeeForm.value;
    const request: UpdateEmployeeRequest = {
      email: formValue.email,
      firstName: formValue.firstName,
      lastName: formValue.lastName,
      roles: formValue.roles,
      enabled: formValue.enabled
    };

    this.employeeService.updateEmployee(this.employeeId, request).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Sucesso',
          detail: 'Funcionário atualizado com sucesso'
        });
        this.submitting = false;
        this.cdr.detectChanges();
        setTimeout(() => this.navigateToList(), 1500);
      },
      error: (error) => {
        console.error('Erro ao atualizar funcionário', error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: error.error?.message || 'Erro ao atualizar funcionário'
        });
        this.submitting = false;
        this.cdr.detectChanges();
      }
    });
  }

  navigateToList(): void {
    this.router.navigate(['/employee']);
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.employeeForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  getFieldError(fieldName: string): string {
    const field = this.employeeForm.get(fieldName);
    if (!field || !field.errors) return '';

    if (field.errors['required']) return 'Campo obrigatório';
    if (field.errors['email']) return 'Email inválido';
    if (field.errors['minlength']) {
      const minLength = field.errors['minlength'].requiredLength;
      return `Mínimo de ${minLength} caracteres`;
    }
    if (field.errors['maxlength']) {
      const maxLength = field.errors['maxlength'].requiredLength;
      return `Máximo de ${maxLength} caracteres`;
    }

    return '';
  }

  private markFormGroupTouched(formGroup: FormGroup): void {
    Object.keys(formGroup.controls).forEach(key => {
      const control = formGroup.get(key);
      control?.markAsTouched();
    });
  }
}
