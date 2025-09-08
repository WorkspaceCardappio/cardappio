import { Component } from '@angular/core';
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  DatePickerComponent,
  ToggleComponent,
  AutocompleteComponent,
  CancelButtonComponent,
  SaveButtonComponent,
  CheckboxComponent,
  ActionsListComponent,
  DialogComponent,
  DropdownTypeFilterComponent, ListComponent
} from 'cardappio-component-hub';
import { ListParams } from "cardappio-component-hub/lib/list/params/list-params.model";
import { DropdownItem } from "cardappio-component-hub/lib/dropdown-menu-list/model/dropdown-item.model";

interface User {
  id: number;
  name: string;
  email: string;
}

interface FormState {
  selectedDate: Date | null;
  selectedUser: User | null;
  selectedMultipleUsers: User[];
  isFeatureEnabled: boolean;
  acceptsTerms: boolean;
  receiveNotifications: boolean;
}

interface CustomAction {
  name: string;
  icon: { name: string; color: string };
  action: (id?: number) => void;
  enabled: boolean;
}

@Component({
  selector: 'app-test',
  standalone: true,
  imports: [
    DatePickerComponent,
    ToggleComponent,
    AutocompleteComponent,
    CancelButtonComponent,
    SaveButtonComponent,
    CheckboxComponent,
    ActionsListComponent,
    DialogComponent,
    DropdownTypeFilterComponent,
    DatePipe,
    FormsModule,
    ListComponent // Add the ListComponent to imports
  ],
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss']
})
export class TestComponent {
  formState: FormState = {
    selectedDate: new Date(),
    selectedUser: null,
    selectedMultipleUsers: [],
    isFeatureEnabled: false,
    acceptsTerms: false,
    receiveNotifications: true
  };

  showCustomDialog = false;

  // Configuration for the ListComponent
  listParams: Partial<ListParams> = {
    route: '/users', // Base route for navigation
    columns: [
      {
        field: 'universo',
        title: 'Universo',
        order: 'none',
        size: 2
      },
      {
        field: 'mundo',
        title: 'Mundo',
        order: 'none',
        size: 2
      },
      {
        field: 'ricardo',
        title: 'Ricardo',
        order: 'none',
        size: 2
      },
      {
        field: 'teste',
        title: 'Teste',
        order: 'none',
        size: 2
      },
      {
        field: 'kenji',
        title: 'Kenji',
        order: 'none',
        size: 2
      }
    ],
    filters: [
      {
        value: 'universo',
        typeValue: 'string'
      },
      {
        label: 'Mundo',
        value: 'mundo',
        typeValue: 'string'
      },
      {
        label: 'Ricardo',
        value: 'ricardo',
        typeValue: 'string'
      },
      {
        label: 'Teste',
        value: 'teste',
        typeValue: 'string'
      },
      {
        label: 'Kenji',
        value: 'kenji',
        typeValue: 'string'
      }
    ] as DropdownItem[]
    // Note: You'll need to add the service property if you have a real service
    // service: yourDataService
  };

  readonly mockUsers: readonly User[] = [
    { id: 1, name: 'João Silva', email: 'joao@email.com' },
    { id: 2, name: 'Maria Santos', email: 'maria@email.com' },
    { id: 3, name: 'Pedro Oliveira', email: 'pedro@email.com' },
    { id: 4, name: 'Ana Costa', email: 'ana@email.com' },
    { id: 5, name: 'Carlos Ferreira', email: 'carlos@email.com' }
  ];

  readonly customActions: readonly CustomAction[] = [
    {
      name: 'view',
      icon: { name: 'fa-solid fa-eye', color: '#28a745' },
      action: (id?: number) => console.log('View action for ID:', id),
      enabled: true
    },
    {
      name: 'download',
      icon: { name: 'fa-solid fa-download', color: '#6c757d' },
      action: (id?: number) => console.log('Download action for ID:', id),
      enabled: true
    }
  ];

  get isFormValid(): boolean {
    return this.formState.acceptsTerms;
  }

  onDateSelected(date: Date | null): void {
    this.formState.selectedDate = date;
    console.log('Date selected:', date);
  }

  searchUsers = (query: string): Observable<User[]> => {
    const filteredUsers = this.mockUsers.filter(user =>
      user.name.toLowerCase().includes(query.toLowerCase()) ||
      user.email.toLowerCase().includes(query.toLowerCase())
    );
    return of(filteredUsers).pipe(delay(300));
  };

  onUserSelected(user: User): void {
    this.formState.selectedUser = user;
    console.log('User selected:', user);
  }

  onMultipleUsersSelected(users: User[]): void {
    this.formState.selectedMultipleUsers = users;
    console.log('Multiple users selected:', users);
  }

  onClearSingleUser(): void {
    this.formState.selectedUser = null;
  }

  onClearMultipleUsers(): void {
    this.formState.selectedMultipleUsers = [];
  }

  displayUserName = (user: User): string => user?.name || '';
  displayUserEmail = (user: User): string => user?.email || '';

  onFeatureToggleChange(enabled: boolean): void {
    this.formState.isFeatureEnabled = enabled;
    console.log('Feature enabled:', enabled);
  }

  onNotificationsToggleChange(enabled: boolean): void {
    this.formState.receiveNotifications = enabled;
    console.log('Notifications enabled:', enabled);
  }

  onTermsChange(accepted: boolean): void {
    this.formState.acceptsTerms = accepted;
    console.log('Terms accepted:', accepted);
  }

  onFilterChange(selectedFilter: unknown): void {
    console.log('Filter changed:', selectedFilter);
  }

  onSave(): void {
    if (!this.isFormValid) {
      console.warn('Cannot save: Form is invalid');
      return;
    }

    console.log('Saving form data:', this.formState);
  }

  onCancel(): void {
    this.resetForm();
    console.log('Form cancelled and reset');
  }

  openDialog(): void {
    this.showCustomDialog = true;
  }

  closeDialog(): void {
    this.showCustomDialog = false;
  }

  confirmDialog(): void {
    console.log('Dialog confirmed');
    this.closeDialog();
  }

  private resetForm(): void {
    this.formState = {
      selectedDate: new Date(),
      selectedUser: null,
      selectedMultipleUsers: [],
      isFeatureEnabled: false,
      acceptsTerms: false,
      receiveNotifications: true
    };
  }

  toggleTerms(): void {
    const newValue = !this.formState.acceptsTerms;
    this.onTermsChange(newValue);
  }
}
