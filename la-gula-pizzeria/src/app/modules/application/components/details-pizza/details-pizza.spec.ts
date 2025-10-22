import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsPizza } from './details-pizza';

describe('DetailsPizza', () => {
  let component: DetailsPizza;
  let fixture: ComponentFixture<DetailsPizza>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailsPizza]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailsPizza);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
