import {Component, inject, Input, TemplateRef, ViewChild} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle,
} from '@angular/material/dialog';

@Component({
  selector: 'modal-generic',
  styleUrl: 'modal.component.scss',
  templateUrl: 'modal.component.html',
  standalone: true,
  imports: [MatButtonModule, MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose],
})
export class ModalComponent {
  @Input() title: string = 'Dialog Title';
  @Input() width: string = '400px';

  readonly dialog = inject(MatDialog);
  @ViewChild('dialogTemplate') dialogTemplate!: TemplateRef<any>;

  open(): void {
    this.dialog.open(this.dialogTemplate, {
      width: this.width,
    });
  }
}
