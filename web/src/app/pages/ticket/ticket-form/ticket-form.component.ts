import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { TicketService } from '../service/ticket.service';

@Component({
  selector: 'app-ticket-form',
  imports: [
    ReactiveFormsModule,
    CommonModule,
],
  providers: [TicketService],
  templateUrl: './ticket-form.component.html',
  styleUrl: './ticket-form.component.scss'
})
export class TicketFormComponent implements OnInit{

  form : FormGroup<any> = new FormGroup({});

  constructor(
    private readonly builder: FormBuilder,
    private service: TicketService,
    private router: Router,
    private route: ActivatedRoute
  ){}

  ngOnInit(): void {
    this.initForm();
    this.getActiveId();
    this.checkRoute();
  };

  private getActiveId(){
    const { id } = this.route.snapshot.params;
    return id;
  }

  private initForm(){
    this.form = this.builder.group({
      id: [''],
      number: [''],
      status: [''],
      owner: [''],
      table: [''],
      active:['']
    });
  }

  private checkRoute(){
    if(this.getActiveId() != 'new'){
      this.loadTicket(this.getActiveId());
    }
  }

  private loadTicket(id: string){
    this.service.findById(id).subscribe(ticket => {
      this.form.patchValue(ticket);
    });
  }

  private navigate(routeToNavigate: string){
    return this.router.navigate([routeToNavigate]);
  }

  create(){
    const { id } = this.route.snapshot.params;
    if(this.form.invalid){
      return
    }

   if (id != 'new') {
      this.service.update(id, this.form.value).subscribe(() => this.router.navigate(['ticket']));
    } else {
      this.service.create(this.form.value).subscribe(() => this.router.navigate(['ticket']));
    }
  }

  cancel(){
    this.navigate('ticket');
  }

  tickets = (query: string): Observable<any> => {
    return this.service.findAll(20, query)
  }
}
