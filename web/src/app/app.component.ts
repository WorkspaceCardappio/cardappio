import { Component, ChangeDetectionStrategy } from '@angular/core';
import { MatDialogActions, MatDialogClose, MatDialogContent } from "@angular/material/dialog";
import { ModalComponent } from "./modal/modal.component";
import { FormsModule } from "@angular/forms";
import { MatButton } from "@angular/material/button";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    MatDialogContent,
    MatDialogActions,
    ModalComponent,
    FormsModule,
    MatDialogClose,
    MatButton
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent {

  title: string = 'title';
  email: any = 'joao.rolo@gmail.com';
  senha: any;
  nome: any;

  openDialog(s: string, s2: string) {

  }

  salvar() {
    console.log("salvou?")
  }
}
