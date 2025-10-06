import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { loadStripe, Stripe, StripeCardElement } from '@stripe/stripe-js';
import { CancelButtonComponent, InputComponent, SaveButtonComponent } from 'cardappio-component-hub';
import { firstValueFrom } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { Payment } from '../model/payment';
import { PaymentRequest } from '../model/payment-request';

@Component({
  selector: 'app-payment',
  imports: [InputComponent, CancelButtonComponent, SaveButtonComponent, ReactiveFormsModule, CommonModule],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.scss'
})
export class PaymentComponent implements OnInit {
  
  form: FormGroup;
  stripe: Stripe | null = null;
  card: StripeCardElement | null = null;
  paymentMessage: string = '';
  hcaptchaToken: string = '';

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.form = this.fb.group({
      amount: [null, [Validators.required, Validators.min(1)]],
      currency: ['brl', Validators.required],
      description: ['']
    });
  }

  async ngOnInit() {

    this.stripe = await loadStripe(environment.stripeKey);

    if (this.stripe) {
      const elements = this.stripe.elements();
      this.card = elements.create('card');
      this.card.mount('#card-element');
    }
  }

  async ngAfterViewInit(): Promise<void> {
    if (typeof window !== 'undefined') {
      console.log('Executando no navegador');
      setTimeout(() => {
        const hcaptcha = (window as any).hcaptcha;
        if (hcaptcha) {
          console.log('hCaptcha disponível');
        } else {
          console.warn('hCaptcha ainda não carregado.');
        }

        (window as any).onHcaptchaSuccess = (token: string) => {
          this.hcaptchaToken = token;
          console.log('hCaptchaToken recebido:', token);
        };
      }, 500);
    } else {
      console.warn('ngAfterViewInit: ambiente sem window (SSR ou build)');
    }
  }

  async createPayment() {
    if (!this.stripe || !this.card) {
      this.paymentMessage = 'Stripe não inicializado';
      return;
    }
    const { paymentMethod, error: pmError } = await this.stripe.createPaymentMethod({
      type: 'card',
      card: this.card,
    });

    const paymentRequest: PaymentRequest = {
      amount: this.form.value.amount,
      currency: this.form.value.currency,
      description: this.form.value.description,
      paymentMethodId: paymentMethod?.id || '',
      hcaptchaToken: this.hcaptchaToken
    };
    console.log("Payment Request ", paymentRequest);
    try {

      const response: Payment = await firstValueFrom(this.http
        .post<Payment>(`${environment.apiUrl}/payment/create`, paymentRequest));
      
      const clientSecret = response.clientSecret;
      if (!clientSecret) {
        this.paymentMessage = 'Erro: clientSecret não recebido';
        return;
      }

      const { paymentIntent, error } = await this.stripe.confirmCardPayment(clientSecret, {
        payment_method: {
          card: this.card,
        },
      });

      if (error) {
        this.paymentMessage = `Erro: ${error.message}`;
      } else if (paymentIntent && paymentIntent.status === 'succeeded') {
        this.paymentMessage = 'Pagamento realizado com sucesso';
        this.form.reset();
        this.hcaptchaToken = '';
        (window as any).hcaptcha.reset();
      } else {
        this.paymentMessage = `Status do pagamento: ${paymentIntent?.status}`;
      }
    } catch (error: any) {
      console.error(error);
      this.paymentMessage = 'Erro ao processar pagamento';
    }
    console.log("mensagem" + this.paymentMessage)
  }

  cancel() {
    this.form.reset();
    this.paymentMessage = '';
  }
}
