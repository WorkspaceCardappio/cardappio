import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { finalize } from 'rxjs';
import LoadUtils from '../../../utils/load-utils';
import { RequestUtils } from '../../../utils/request-utils';
import { MenuService } from "../service/menu.service";

@Component({
  selector: 'app-cardapio-list',
  imports: [
    TableModule,
    ButtonModule,
    IconFieldModule,
    InputIconModule,
    CommonModule,
    FormsModule,
    InputTextModule,
    RouterLink,
    BreadcrumbModule,
  ],
  providers: [
    MenuService
  ],
  templateUrl: './menu-list.component.html',
  styleUrl: './menu-list.component.scss'
})
export class CardapioListComponent {

  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [{ label: 'Cardápio', routerLink: '/menu' }];

  menus: any[] = [];
  totalRecords: number = 0;
  loading = false;

  constructor(
    private service: MenuService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.load(LoadUtils.getDefault());
  }

  clear(table: any) {
    table.clear();
  }

  onEdit(id: any) {
    this.router.navigate([`menu`, id]);
  }

  onDelete(id: any) {
    this.service.delete(id).subscribe(() => this.load(LoadUtils.getDefault()));
  }

  load(event: TableLazyLoadEvent) {
    this.loading = true;

    const request = RequestUtils.build(event);

    this.service
      .findAllDTO(request)
      .pipe(
        finalize(() => {
          this.loading = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: (response) => {
          this.menus = response.content;
          this.totalRecords = response.totalElements;
        },
        error: () => {},
      });
  }

}
