import {
  Component,
  inject,
  input,
  output,
  TemplateRef,
  viewChild,
  DestroyRef,
  signal,
  computed
} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MatButtonModule } from '@angular/material/button';
import {
  MatDialog,
  MatDialogRef,
  MatDialogTitle,
  MatDialogContent,
  MatDialogActions,
  MatDialogConfig,
} from '@angular/material/dialog';
import { NgClass } from "@angular/common";
import { CancelButtonComponent, GenericButtonComponent, SaveButtonComponent } from "cardappio-component-hub";
import { MatIcon } from "@angular/material/icon";

export interface ModalConfig {
  title?: string;
  width?: string;
  cancelText?: string;
  confirmText?: string;
  disableClose?: boolean;
}

export type ModalType = 'warning' | 'edit' | 'delete' | 'post';

@Component({
  selector: 'app-modal-generic',
  styleUrl: './modal.component.scss',
  templateUrl: './modal.component.html',
  standalone: true,
  imports: [MatButtonModule, MatDialogTitle, MatDialogContent, MatDialogActions, NgClass, SaveButtonComponent, GenericButtonComponent, CancelButtonComponent, MatIcon],
})
export class ModalComponent {

  readonly type = input<ModalType>('warning');
  readonly title = input<string>('Dialog Title');
  readonly width = input<string>('400px');
  readonly cancelText = input<string>('Cancelar');
  readonly confirmText = input<string>();
  readonly disableClose = input<boolean>(false);

  readonly confirmed = output<void>();
  readonly cancelled = output<void>();
  readonly closed = output<void>();

  private readonly dialog = inject(MatDialog);
  private readonly destroyRef = inject(DestroyRef);

  readonly dialogTemplate = viewChild.required<TemplateRef<unknown>>('dialogTemplate');

  private dialogRef = signal<MatDialogRef<unknown> | null>(null);
  readonly isOpen = computed(() => this.dialogRef() !== null);


  open(): void {
    const config: MatDialogConfig = {
      width: this.width(),
      disableClose: this.disableClose(),
      autoFocus: 'dialog',
      restoreFocus: true,
    };

    const ref = this.dialog.open(this.dialogTemplate(), config);
    this.dialogRef.set(ref);

    ref.afterClosed()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(() => {
        this.dialogRef.set(null);
        this.closed.emit();
      });
  }


  close(): void {
    const ref = this.dialogRef();
    if (ref) {
      ref.close();
      this.dialogRef.set(null);
    }
  }


  handleConfirm(): void {
    this.confirmed.emit();
    this.close();
  }


  handleCancel(): void {
    this.cancelled.emit();
    this.close();
  }
}
