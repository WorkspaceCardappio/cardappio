import { Component, ChangeDetectionStrategy } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatNativeDateModule } from '@angular/material/core';

import {
  LeftMenuComponent,
  HeaderComponent,
  CheckboxComponent,
  ActionsListComponent,
  AutocompleteComponent,
  CancelButtonComponent,
  GenericButtonComponent,
  SaveButtonComponent,
  DatePickerComponent,
  DialogComponent,
  DropdownMenuListComponent,
  DropdownTypeFilterComponent,
  InputComponent,
  FilterHeaderComponent,
  ListComponent
} from "cardappio-component-hub";
import { RouterOutlet } from "@angular/router";


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [

    MatDatepickerModule,
    MatInputModule,
    MatFormFieldModule,
    MatNativeDateModule,

    LeftMenuComponent,
    HeaderComponent,
    CheckboxComponent,
    ActionsListComponent,
    AutocompleteComponent,
    CancelButtonComponent,
    GenericButtonComponent,
    SaveButtonComponent,
    DatePickerComponent,
    DialogComponent,
    DropdownMenuListComponent,
    DropdownTypeFilterComponent,
    InputComponent,
    FilterHeaderComponent,
    ListComponent,
    RouterOutlet
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent {

  title: string = 'title';

  aoAlterarNome(valor: string) {
    this.title = valor;
    console.log('Nome alterado para:', this.title);
  }
}
