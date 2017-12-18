import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../shared';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import {
    adminState,
    AuditsComponent,
    UserMgmtComponent,
    UserDialogComponent,
    UserDeleteDialogComponent,
    UserMgmtDetailComponent,
    UserMgmtDialogComponent,
    UserMgmtDeleteDialogComponent,
    LogsComponent,
    FafiMetricsMonitoringModalComponent,
    FafiMetricsMonitoringComponent,
    FafiHealthModalComponent,
    FafiHealthCheckComponent,
    FafiConfigurationComponent,
    FafiDocsComponent,
    AuditsService,
    FafiConfigurationService,
    FafiHealthService,
    FafiMetricsService,
    LogsService,
    UserResolvePagingParams,
    UserResolve,
    UserModalService
} from './';

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(adminState),
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    ],
    declarations: [
        AuditsComponent,
        UserMgmtComponent,
        UserDialogComponent,
        UserDeleteDialogComponent,
        UserMgmtDetailComponent,
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
        LogsComponent,
        FafiConfigurationComponent,
        FafiHealthCheckComponent,
        FafiHealthModalComponent,
        FafiDocsComponent,
        FafiMetricsMonitoringComponent,
        FafiMetricsMonitoringModalComponent
    ],
    entryComponents: [
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
        FafiHealthModalComponent,
        FafiMetricsMonitoringModalComponent,
    ],
    providers: [
        AuditsService,
        FafiConfigurationService,
        FafiHealthService,
        FafiMetricsService,
        LogsService,
        UserResolvePagingParams,
        UserResolve,
        UserModalService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiAdminModule {}
