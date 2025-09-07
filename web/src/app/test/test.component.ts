import { Component } from '@angular/core';
import {
  InputComponent,
  ToggleComponent,
  DatePickerComponent,
  AutocompleteComponent,
  CancelButtonComponent,
  SaveButtonComponent,
  CheckboxComponent,
  ActionsListComponent,
  DialogComponent,
  DropdownTypeFilterComponent
} from "cardappio-component-hub";
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { DatePipe } from "@angular/common";
import { FormsModule } from "@angular/forms";

@Component({
  selector: 'app-test',
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
    FormsModule
  ],
  standalone: true,
  templateUrl: './test.component.html',
  styleUrl: './test.component.scss'
})
export class TestComponent {
  selectedDate: Date | null = new Date();
  selectedUser: any = null;
  selectedMultipleItems: any[] = [];

  // Form state
  isEnabled: boolean = false;
  acceptTerms: boolean = false;
  receiveNotifications: boolean = true;

  // Dialog state
  showCustomDialog: boolean = false;

  // Mock data for demonstration
  mockUsers = [
    { id: 1, name: 'João Silva', email: 'joao@email.com' },
    { id: 2, name: 'Maria Santos', email: 'maria@email.com' },
    { id: 3, name: 'Pedro Oliveira', email: 'pedro@email.com' },
    { id: 4, name: 'Ana Costa', email: 'ana@email.com' },
    { id: 5, name: 'Carlos Ferreira', email: 'carlos@email.com' }
  ];

  // Custom actions for actions list
  customActions = [
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

  // Existing methods...
  onDateSelected(date: Date | null) {
    console.log('Selected date:', date);
    this.selectedDate = date;
  }

  searchUsers = (query: string): Observable<any[]> => {
    console.log('Searching for:', query);

    const filteredUsers = this.mockUsers.filter(user =>
      user.name.toLowerCase().includes(query.toLowerCase()) ||
      user.email.toLowerCase().includes(query.toLowerCase())
    );

    return of(filteredUsers).pipe(delay(300));
  }

  onUserSelected(user: any) {
    console.log('Selected user:', user);
    this.selectedUser = user;
  }

  onMultipleUsersSelected(users: any[]) {
    console.log('Selected users:', users);
    this.selectedMultipleItems = users;
  }

  onClearUsers() {
    console.log('Cleared selection');
    this.selectedUser = null;
  }

  onClearMultipleUsers() {
    console.log('Cleared multiple selection');
    this.selectedMultipleItems = [];
  }

  displayUserName = (user: any) => user?.name || '';
  displayUserEmail = (user: any) => user?.email || '';

  onSave() {
    console.log('Save clicked!');
    console.log('Form data:', {
      selectedDate: this.selectedDate,
      selectedUser: this.selectedUser,
      selectedMultipleItems: this.selectedMultipleItems,
      isEnabled: this.isEnabled,
      acceptTerms: this.acceptTerms,
      receiveNotifications: this.receiveNotifications
    });
  }

  onCancel() {
    console.log('Cancel clicked!');
    this.selectedDate = new Date();
    this.selectedUser = null;
    this.selectedMultipleItems = [];
    this.isEnabled = false;
    this.acceptTerms = false;
    this.receiveNotifications = true;
  }

  onToggleChange(value: boolean) {
    console.log('Toggle changed:', value);
    this.isEnabled = value;
  }

  onTermsChange(value: boolean) {
    console.log('Terms checkbox changed:', value);
    this.acceptTerms = value;
  }

  onNotificationsChange(value: boolean) {
    console.log('Notifications toggle changed:', value);
    this.receiveNotifications = value;
  }

  // New methods for new components
  onFilterChange(selectedFilter: any) {
    console.log('Filter changed:', selectedFilter);
  }

  openCustomDialog() {
    this.showCustomDialog = true;
  }

  closeCustomDialog() {
    this.showCustomDialog = false;
  }

  confirmCustomDialog() {
    console.log('Custom dialog confirmed!');
    this.showCustomDialog = false;
  }
}
