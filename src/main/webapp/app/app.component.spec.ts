import { PRECONNECT_CHECK_BLOCKLIST } from '@angular/common';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { RouterTestingModule } from '@angular/router/testing';

import { AppComponent } from './app.component';
import LoginComponent from './login/login.component';

describe('App Component', () => {
  let comp: AppComponent;
  // let oauth2AuthService: Oauth2AuthService;
  let fixture: ComponentFixture<AppComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([])],
      providers: [{ provide: PRECONNECT_CHECK_BLOCKLIST, useValue: 'https://jestjs.io' }],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    comp = fixture.componentInstance;
    // oauth2AuthService = TestBed.inject(Oauth2AuthService);
  });

  describe('ngOnInit', () => {
    it('should have appName', () => {
      // WHEN
      fixture.detectChanges();

      // THEN
      expect(comp.appName).toEqual('jhlite110');
    });

    it('should display login component', () => {
      fixture.detectChanges();

      expect(fixture.debugElement.query(By.directive(LoginComponent))).toBeTruthy();
    });
  });
});
