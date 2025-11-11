import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { MessageModule } from 'primeng/message';
import { AuthService } from '../../core/auth/auth.service';
import { LoginCredentials } from '../../core/models/user.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ButtonModule,
    InputTextModule,
    PasswordModule,
    MessageModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  loading = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', ]
    });
  }

  async onSubmit() {
    if (this.loginForm.valid) {
      this.loading = true;
      this.errorMessage = '';

      try {
        const credentials: LoginCredentials = {
          username: this.loginForm.value.username,
          password: this.loginForm.value.password
        };

        await this.authService.loginWithCredentials(credentials);
      } catch (error: any) {
        console.error('Erro ao fazer login:', error);

        if (error.status === 401) {
          this.errorMessage = 'Usuário ou senha inválidos';
        } else if (error.status === 0) {
          this.errorMessage = 'Erro de conexão com o servidor';
        } else {
          this.errorMessage = 'Erro ao fazer login. Tente novamente.';
        }
      } finally {
        this.loading = false;
      }
    } else {
      this.markFormGroupTouched(this.loginForm);
    }
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.loginForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  getErrorMessage(fieldName: string): string {
    const field = this.loginForm.get(fieldName);
    if (field?.hasError('required')) {
      return 'Campo obrigatório';
    }
    if (field?.hasError('minlength')) {
      return 'Mínimo 8 caracteres';
    }
    return '';
  }

  forgotPassword() {
    // TODO: Implementar recuperação de senha
    console.log('Recuperar senha');
  }

  private markFormGroupTouched(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(key => {
      const control = formGroup.get(key);
      control?.markAsTouched();

      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }
}
