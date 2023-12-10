import { HarnessLoader } from '@angular/cdk/testing';
import { TestbedHarnessEnvironment } from '@angular/cdk/testing/testbed';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MatDialogHarness } from '@angular/material/dialog/testing';
import { of } from 'rxjs';

import HealthComponent from './health.component';
import { Health } from './health.model';
import { HealthService } from './health.service';

describe('HealthComponent', () => {
  let comp: HealthComponent;
  let fixture: ComponentFixture<HealthComponent>;
  let service: HealthService;
  let loader: HarnessLoader;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    })
      .overrideTemplate(HealthComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HealthComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(HealthService);
    loader = TestbedHarnessEnvironment.documentRootLoader(fixture);
  });

  describe('refresh', () => {
    it('should call refresh on init', () => {
      // GIVEN
      const health: Health = { status: 'UP', components: { mail: { status: 'UP', details: { mailDetail: 'mail' } } } };
      jest.spyOn(service, 'checkHealth').mockReturnValue(of(health));

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.checkHealth).toHaveBeenCalled();
      expect(
        service.checkHealth().subscribe({
          next: () => expect(comp.datasource).toEqual([{ details: { mailDetail: 'mail' }, key: 'mail', status: 'UP' }]),
        })
      );
    });

    it('should call checkHealth on refresh', done => {
      // GIVEN
      const health: Health = { status: 'UP', components: { mail: { status: 'UP', details: { mailDetail: 'mail' } } } };
      jest.spyOn(service, 'checkHealth').mockImplementation(() => of(health));

      // WHEN
      comp.refresh();
      done();

      // THEN
      expect(service.checkHealth).toHaveBeenCalled();
    });
  });

  describe('showHealth', () => {
    it('should open dialog', async () => {
      // GIVEN
      const health: Health = {
        status: 'UP',
        components: null,
      };

      // WHEN
      comp.showHealth(health);

      // THEN
      const dialogs = await loader.getAllHarnesses(MatDialogHarness);
      expect(dialogs.length).toBe(1);
    });
  });

  describe('getBadgeClass', () => {
    it('should get badge class', () => {
      const upBadgeClass = comp.getBadgeClass('UP');
      const downBadgeClass = comp.getBadgeClass('DOWN');
      expect(upBadgeClass).toEqual('bg-success');
      expect(downBadgeClass).toEqual('bg-danger');
    });
  });
});
