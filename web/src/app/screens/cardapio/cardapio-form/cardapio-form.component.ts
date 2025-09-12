
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
import { CardapioService } from "./cardapio.service";

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
    ListComponent,
    DialogComponent
  ],
  templateUrl: './cardapio-form.component.html',
  styleUrls: ['./cardapio-form.component.scss']
})
export class TestComponent {

  constructor(private cardapioService: CardapioService) {}


  formState: FormState = {
    selectedDate: new Date(),
    selectedUser: null,
    selectedMultipleUsers: [],
    isFeatureEnabled: false,
    acceptsTerms: false,
    receiveNotifications: true
  };

  showCustomDialog = false;

  listParams: Partial<ListParams> = {
    route: '/users',
    columns: [
      {
        field: 'mundo',
        title: 'Mundo',
        order: 'none',
        size: 1
      },
      {
        field: 'ricardo',
        title: 'Ricardo',
        order: 'none',
        size: 1
      },
      {
        field: 'teste',
        title: 'Teste',
        order: 'none',
        size: 1
      },
      {
        field: 'kenjisssssss',
        title: 'www',
        order: 'none',
        size: 1
      }
    ],
    filters: [
      {
        value: 'universo',
        typeValue: 'string'
      },
      {
        label: 'teswwwwwte',
        value: 'teswwwwwte',
        typeValue: 'string'
      },
      {
        label: 'Kenji',
        value: 'kenji',
        typeValue: 'string'
      }
    ] as DropdownItem[]
  };

  test(){
    let oi = this.cardapioService.findAll();
  }

}
