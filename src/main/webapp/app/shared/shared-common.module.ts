import { NgModule, LOCALE_ID } from '@angular/core';
import { Title } from '@angular/platform-browser';

import {
    FafiSharedLibsModule,
    JhiLanguageHelper,
    FindLanguageFromKeyPipe,
    FafiAlertComponent,
    FafiAlertErrorComponent
} from './';

@NgModule({
    imports: [
        FafiSharedLibsModule
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        FafiAlertComponent,
        FafiAlertErrorComponent
    ],
    providers: [
        JhiLanguageHelper,
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'en'
        },
    ],
    exports: [
        FafiSharedLibsModule,
        FindLanguageFromKeyPipe,
        FafiAlertComponent,
        FafiAlertErrorComponent
    ]
})
export class FafiSharedCommonModule {}
