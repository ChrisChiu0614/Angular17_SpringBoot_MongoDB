import { Component } from '@angular/core';
import { Tutorial } from '../../models/tutorial.model';
import { TutorialService } from '../../services/tutorial.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-add-tutorial',
  standalone: false,
  templateUrl: './add-tutorial.component.html',
  styleUrl: './add-tutorial.component.css'
})
export class AddTutorialComponent {
  tutorial: Tutorial = {
    title: '',
    description: '',
    published: false,
    html: ''
  };
  submitted = false;

  constructor(private tutorialService: TutorialService){}

  saveTutorial(): void{
    const data = {
      title: this.tutorial.title,
      description: this.tutorial.description,
      html: this.tutorial.html
    };

    this.tutorialService.create(data)
    .subscribe({
      next:(res)=>{
        console.log(res);
        this.submitted = true;
      },
      error:(e)=>console.log(e)
    });
  }

  newTutorial(): void{
    this.submitted = false;
    this.tutorial = {
      title: '',
      description: '',
      published: false,
      html: ''
    };
  }

}
