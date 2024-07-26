import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { PassengerService } from './passenger.service';
import { PassengerBatch } from './passengerbatch';
import { DecimalPipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCheckbox, MatCheckboxChange } from '@angular/material/checkbox';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { MatRadioModule } from '@angular/material/radio';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [
        RouterOutlet,
        MatTableModule,
        MatPaginatorModule,
        MatFormFieldModule,
        MatInputModule,
        DecimalPipe,
        MatCheckbox,
        MatCheckboxModule,
        MatButtonModule,
        MatRadioModule,
    ],
    template: `
        <div class="container">
            <section class="filters-section">
                <h4>Filters(show only):</h4>
                <p><mat-checkbox name="survived" (change)="onCheckBoxChange($event)">Survived</mat-checkbox></p>
                <p><mat-checkbox name="age" (change)="onCheckBoxChange($event)">Adults</mat-checkbox></p>
                <p><mat-checkbox name="sex" (change)="onCheckBoxChange($event)">Males</mat-checkbox></p>
                <p><mat-checkbox name="relatives" (change)="onCheckBoxChange($event)">Single</mat-checkbox></p>
                <h4>Sort by:</h4>
                <mat-radio-group class="radio-group" (input)="onRadioChange($event)">
                    <mat-radio-button value="name-asc">Name ascending</mat-radio-button>
                    <mat-radio-button value="name-desc">Name descending</mat-radio-button>
                    <mat-radio-button value="age-asc">Age ascending</mat-radio-button>
                    <mat-radio-button value="age-desc">Age descending</mat-radio-button>
                    <mat-radio-button value="fare-asc">Fare ascending</mat-radio-button>
                    <mat-radio-button value="fare-desc">Fare descending</mat-radio-button>
                    <mat-radio-button value="">Default</mat-radio-button>
                </mat-radio-group>
                <p><button mat-stroked-button (click)="requestData()">Apply</button></p>
            </section>
            <section class="table-section">
                <mat-form-field>
                    <mat-label>Search by name</mat-label>
                    <input matInput (keyup)="searchByName($event)" placeholder="Passenger's name" #input />
                </mat-form-field>
                <div class="table-container mat-elevation-z8">
                    <div class="passengers-table-container">
                        <table mat-table [dataSource]="this.passengerBatch.passengers" class="passengers-table" matSort>
                            <!-- Id Column -->
                            <ng-container matColumnDef="id">
                                <th mat-header-cell *matHeaderCellDef>Id</th>
                                <td mat-cell *matCellDef="let row">{{ row.id }}</td>
                                <td mat-footer-cell *matFooterCellDef></td>
                            </ng-container>

                            <!-- Survived Column -->
                            <ng-container matColumnDef="survived">
                                <th mat-header-cell *matHeaderCellDef>Survived</th>
                                <td mat-cell *matCellDef="let row">{{ row.survived }}</td>
                                <td mat-footer-cell *matFooterCellDef>
                                    Survived: {{ this.passengerBatch.total.survived }}
                                </td>
                            </ng-container>

                            <!-- PClass Column -->
                            <ng-container matColumnDef="pclass">
                                <th mat-header-cell *matHeaderCellDef>PClass</th>
                                <td mat-cell *matCellDef="let row">{{ row.pclass }}</td>
                                <td mat-footer-cell *matFooterCellDef></td>
                            </ng-container>

                            <!-- Name Column -->
                            <ng-container matColumnDef="name">
                                <th mat-header-cell *matHeaderCellDef>Name</th>
                                <td mat-cell *matCellDef="let row">{{ row.name }}</td>
                                <td mat-footer-cell *matFooterCellDef></td>
                            </ng-container>

                            <!-- Age Column -->
                            <ng-container matColumnDef="age">
                                <th mat-header-cell *matHeaderCellDef>Age</th>
                                <td mat-cell *matCellDef="let row">{{ row.age }}</td>
                                <td mat-footer-cell *matFooterCellDef></td>
                            </ng-container>

                            <!-- Sex Column -->
                            <ng-container matColumnDef="sex">
                                <th mat-header-cell *matHeaderCellDef>Sex</th>
                                <td mat-cell *matCellDef="let row">{{ row.sex }}</td>
                                <td mat-footer-cell *matFooterCellDef></td>
                            </ng-container>

                            <!-- Sibling/Spouses Aboard Column -->
                            <ng-container matColumnDef="siblingsSpouses">
                                <th mat-header-cell *matHeaderCellDef>Siblings/Spouses Aboard</th>
                                <td mat-cell *matCellDef="let row">{{ row.siblingsSpouses }}</td>
                                <td mat-footer-cell *matFooterCellDef>Have relatives:</td>
                            </ng-container>

                            <!-- Parents/Children Aboard Column -->
                            <ng-container matColumnDef="parentsChildren">
                                <th mat-header-cell *matHeaderCellDef>Parents/Children Aboard</th>
                                <td mat-cell *matCellDef="let row">{{ row.parentsChildren }}</td>
                                <td mat-footer-cell *matFooterCellDef>
                                    {{ this.passengerBatch.total.haveRelatives }}
                                </td>
                            </ng-container>

                            <!--Fare  Column -->
                            <ng-container matColumnDef="fare">
                                <th mat-header-cell *matHeaderCellDef>Fare</th>
                                <td mat-cell *matCellDef="let row">
                                    {{ row.fare | number : '1.0-2' }}
                                </td>
                                <td mat-footer-cell *matFooterCellDef>
                                    Total: {{ this.passengerBatch.total.fare | number : '1.0-2' }}
                                </td>
                            </ng-container>

                            <tr mat-header-row *matHeaderRowDef="displayedColumns; sticky: true"></tr>
                            <tr mat-row *matRowDef="let row; columns: displayedColumns"></tr>
                            <tr mat-footer-row *matFooterRowDef="displayedColumns; sticky: true"></tr>
                        </table>
                    </div>

                    <mat-paginator
                        [pageSizeOptions]="[25, 50, 75, 100]"
                        [pageSize]="this.parametersMap.get('limit')"
                        [pageIndex]="this.parametersMap.get('offset')"
                        [length]="this.passengerBatch.total.passengers"
                        aria-label="Select page"
                        (page)="paginatorEvent($event)"
                    ></mat-paginator>
                </div>
            </section>
        </div>
        <router-outlet />
    `,
    styleUrl: './app.component.css',
})
export class AppComponent {
    title = 'titanic-app';
    passengerService: PassengerService = inject(PassengerService);
    displayedColumns: string[] = [
        'id',
        'survived',
        'pclass',
        'name',
        'age',
        'sex',
        'siblingsSpouses',
        'parentsChildren',
        'fare',
    ];
    passengerBatch!: PassengerBatch;
    parametersMap!: Map<string, string>;

    constructor(private _httpClient: HttpClient) {
        // creates and initializes parametersMap with default values
        this.parametersMap = new Map<string, string>();
        this.parametersMap.set('offset', '0');
        this.parametersMap.set('limit', '50');
        this.parametersMap.set('name', '');
        this.parametersMap.set('sort', '');
        this.parametersMap.set('survived', '');
        this.parametersMap.set('age', '');
        this.parametersMap.set('sex', '');
        this.parametersMap.set('relatives', '');
        // initialization with default value (until data is loaded from the server) for the table
        this.passengerBatch = {
            passengers: [
                {
                    id: 0,
                    survived: false,
                    pclass: '',
                    name: '',
                    age: 0,
                    sex: '',
                    siblingsSpouses: 0,
                    parentsChildren: 0,
                    fare: 0,
                },
            ],
            total: { survived: 0, haveRelatives: 0, fare: 0, passengers: 0 },
        };
        this.requestData();
    }

    // Request data from the server.
    async requestData() {
        this.passengerService.getPassengers(this.parametersMap).subscribe((passengerBatch) => {
            this.passengerBatch = passengerBatch;
            console.log(this.passengerBatch.passengers);
        });
    }

    // Handles events for changes (page)|(items per page).
    paginatorEvent(pageEvent: PageEvent) {
        this.parametersMap.set('offset', pageEvent.pageIndex.toString());
        this.parametersMap.set('limit', pageEvent.pageSize.toString());
        this.requestData();
    }

    // Handles events for entering search queries.
    searchByName(event: Event) {
        if ((event as KeyboardEvent).code == 'Enter') {
            const name = (event.target as HTMLInputElement).value;
            this.parametersMap.set('name', name);
            this.requestData();
        }
    }

    // Handles events for filter checkboxs.
    onCheckBoxChange(event: MatCheckboxChange) {
        switch (event.source.name) {
            case 'survived':
                this.parametersMap.set('survived', `${event.checked}`);
                break;
            case 'age':
                this.parametersMap.set('age', `${event.checked ? 'adult' : ''}`);
                break;
            case 'sex':
                this.parametersMap.set('sex', `${event.checked ? 'male' : ''}`);
                break;
            case 'relatives':
                this.parametersMap.set('relatives', `${event.checked ? 'single' : ''}`);
                break;
        }
    }

    // Handles events for sort radio buttons.
    onRadioChange(event: Event) {
        this.parametersMap.set('sort', (event.target as HTMLInputElement).value);
    }
}
