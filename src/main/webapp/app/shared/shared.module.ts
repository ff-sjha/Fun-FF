import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    FafiSharedLibsModule,
    FafiSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    LoginModalService,
    FafiLoginModalComponent,
    Principal,
    HasAnyAuthorityDirective,
    FafiSocialComponent,
    SocialService,
} from './';

@NgModule({
    imports: [
        FafiSharedLibsModule,
        FafiSharedCommonModule
    ],
    declarations: [
        FafiSocialComponent,
        FafiLoginModalComponent,
        HasAnyAuthorityDirective
    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        SocialService,
        UserService,
        DatePipe
    ],
    entryComponents: [FafiLoginModalComponent],
    exports: [
        FafiSharedCommonModule,
        FafiSocialComponent,
        FafiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class FafiSharedModule {}
