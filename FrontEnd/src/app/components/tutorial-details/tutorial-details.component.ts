import { Component, Input, input, OnInit } from '@angular/core';
import { Tutorial } from '../../models/tutorial.model';
import { TutorialService } from '../../services/tutorial.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-tutorial-details',
  standalone: false,
  templateUrl: './tutorial-details.component.html',
  styleUrl: './tutorial-details.component.css'
})
export class TutorialDetailsComponent implements OnInit{
  @Input() viewMode = false;

  @Input() currentTutorial: Tutorial = {
    title: '',
    description: '',
    published: false,
    html: ''
  };

  message = '';

  constructor(
    private tutorialService: TutorialService,
    private route: ActivatedRoute,
    private router: Router
  ){}

  ngOnInit(): void {
    if(!this.viewMode){
      this.message = '';
      this.getTutorial(this.route.snapshot.params["id"]);
    }
  }
 
  getTutorial(id: string):void{
    this.tutorialService.get(id)
    .subscribe({
      next:(data)=>{
        this.currentTutorial = data;
        console.log(data);
      },
      error:(e)=>{
        console.log(e);
      }
    });
  }

  updatePublished(status: boolean):void{
    const data ={
      title: this.currentTutorial.title,
      describe: this.currentTutorial.description,
      published: status,
      html: this.currentTutorial.html
    };

    this.message = '';

    this.tutorialService.update(this.currentTutorial.id, data)
    .subscribe({
      next: (res) =>{
        console.log(res);
        this.currentTutorial.published = status;
        //this.message = res.message? res.message: 'The status was updated successfully!';
      },
      error: (e)=>{
        console.log(e);
      }
    });

  }

  updateTutorial():void{
    this.message = '';

    this.tutorialService.update(this.currentTutorial.id, this.currentTutorial)
    .subscribe({
      next: (res) =>{
        console.log(res);
        //this.message = res.message? res.message : 'This tutorial was updated successfully!';
      },
      error:(e)=>{
        console.log(e);
      }

    });
  }

  deleteTutorial():void{
    this.message = '';
    console.log(this.currentTutorial.id);
    this.tutorialService.delete(this.currentTutorial.id)
    .subscribe({
      next: (res)=>{
        console.log(res);
        this.router.navigate(['/tutorials']);
      },
      error:(e)=>{
        console.log('fail');
        console.log(e);
      }

    });
  }

}
