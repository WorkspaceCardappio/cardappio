// import { animate, state, style, transition, trigger } from '@angular/animations';
// import { Component, Input, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
// import { ActivatedRoute, Router } from '@angular/router';
// import { AutocompleteComponent, CancelButtonComponent, InputComponent, SaveButtonComponent } from "cardappio-component-hub";
// import { Observable } from 'rxjs';
// import { ProductService } from '../../product/service/product.service';

// @Component({
//   selector: 'app-additional',
//   imports: [SaveButtonComponent, AutocompleteComponent, InputComponent, ReactiveFormsModule, CancelButtonComponent],
//   templateUrl: './additional-modal.component.html',
//   styleUrl: './additional-modal.component.scss',
//   animations: [
//     trigger('expandCollapse', [
//       state('collapsed', style({
//         height: '0px',
//         opacity: 0,
//         overflow: 'hidden'
//       })),
//       state('expanded', style({
//         height: '*',
//         opacity: 1,
//         overflow: 'hidden'
//       })),
//       transition('collapsed <=> expanded', [
//         animate('300ms ease-in-out')
//       ])
//     ])
//   ]
// })
  
// export class AdditionalComponent implements OnInit {
//   @Input() expanded = false;
//   form: FormGroup<any> = new FormGroup({});

//   constructor(
//     private readonly builder: FormBuilder,
//     private service: ProductService,
//     private router: Router,
//     private route: ActivatedRoute
//   ) { }

//     ngOnInit(): void {
//       this.initForm();
//   };

//   private initForm(): void {
//     this.form = this.builder.group({
//       id: [''],
//       price: ['', [Validators.required, Validators.min(0)]],
//       note: ['']
//     })
//   }  

//   product = (query: string): Observable<any[]> => {
  
//       const searchs = [];
  
//       if (query) {
//         searchs.push(`name=ilike=${query}%`);
//       }
  
//       return this.service.findAll(20, searchs.join(';'));
//     }

// }
