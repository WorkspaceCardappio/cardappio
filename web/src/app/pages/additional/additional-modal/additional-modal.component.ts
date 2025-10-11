import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AutocompleteComponent, CancelButtonComponent, InputComponent, SaveButtonComponent } from "cardappio-component-hub";
import { Observable } from 'rxjs';
import { ProductService } from '../../product/service/product.service';
@Component({
  selector: 'app-additional',
  imports: [SaveButtonComponent, AutocompleteComponent, InputComponent, ReactiveFormsModule, CancelButtonComponent],
  templateUrl: './additional-modal.component.html',
  styleUrl: './additional-modal.component.scss'
})
  
export class AdditionalComponent implements OnInit {
  
  form: FormGroup<any> = new FormGroup({});

  @ViewChild('dialog') dialog!: ElementRef<HTMLDialogElement>;

  constructor(
    private readonly builder: FormBuilder,
    private service: ProductService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

    ngOnInit(): void {
      this.initForm();
  };

  private initForm(): void {
    this.form = this.builder.group({
      id: [''],
      name: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      note: ['']
    })
  }

  openDialog(): void {
    this.dialog.nativeElement.showModal();
  }

  closeDialog(): void {
    this.dialog.nativeElement.close();
  }

  product = (query: string): Observable<any[]> => {
  
      const searchs = [];
  
      if (query) {
        searchs.push(`name=ilike=${query}%`);
      }
  
      return this.service.findAll(20, searchs.join(';'));
    }

}
