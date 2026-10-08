import { bootstrapApplication } from '@angular/platform-browser';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  standalone: true,
  template: `
    <main class="page">
      <h1>VMart</h1>
      <p>E-commerce platform foundation is ready.</p>
    </main>
  `,
  styles: [`
    .page {
      min-height: 100vh;
      display: grid;
      place-content: center;
      text-align: center;
      font-family: system-ui, sans-serif;
    }
  `]
})
class AppComponent {}

bootstrapApplication(AppComponent)
  .catch(error => console.error(error));
