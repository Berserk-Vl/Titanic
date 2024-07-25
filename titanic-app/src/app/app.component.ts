import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { PassengerService } from './passenger.service';
import { PassengerBatch } from './passengerbatch';
import { Observable } from 'rxjs';
import { AsyncPipe, DecimalPipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [
        RouterOutlet,
        MatTableModule,
        MatPaginatorModule,
        MatFormFieldModule,
        MatInputModule,
        AsyncPipe,
        DecimalPipe,
    ],
    template: `
        @if (data$ | async; as data){
        <mat-form-field>
            <mat-label>Search by name</mat-label>
            <input matInput (keyup)="searchByName($event)" placeholder="Passenger's name" #input />
        </mat-form-field>
        <div class="table-container mat-elevation-z8">
            <div class="passengers-table-container">
                <table mat-table [dataSource]="data.passengers" class="passengers-table" matSort>
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
                        <td mat-footer-cell *matFooterCellDef>Survived: {{ data.total.survived }}</td>
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
                            {{ data.total.haveRelatives }}
                        </td>
                    </ng-container>

                    <!--Fare  Column -->
                    <ng-container matColumnDef="fare">
                        <th mat-header-cell *matHeaderCellDef>Fare</th>
                        <td mat-cell *matCellDef="let row">
                            {{ row.fare | number : '1.0-2' }}
                        </td>
                        <td mat-footer-cell *matFooterCellDef>Total: {{ data.total.fare | number : '1.0-2' }}</td>
                    </ng-container>

                    <tr mat-header-row *matHeaderRowDef="displayedColumns sticky: true"></tr>
                    <tr mat-row *matRowDef="let row; columns: displayedColumns"></tr>
                    <tr mat-footer-row *matFooterRowDef="displayedColumns sticky: true"></tr>
                </table>
            </div>

            <mat-paginator
                [pageSizeOptions]="[25, 50, 75, 100]"
                pageSize="50"
                [length]="data.total.passengers"
                aria-label="Select page"
                (page)="paginatorEvent($event)"
            ></mat-paginator>
        </div>
        }
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
    data$!: Observable<PassengerBatch>;
    passengerBatch!: PassengerBatch;

    page = 0;
    length = 50;
    name = '';
    sort = '';
    survived = '';
    age = '';
    sex = '';
    relatives = '';

    constructor(private _httpClient: HttpClient) {
        let initialParametersMap = new Map<string, string>();
        initialParametersMap.set('offset', this.page.toString());
        initialParametersMap.set('limit', this.length.toString());
        this.data$ = this.passengerService.getPassengers(initialParametersMap);
    }

    paginatorEvent(pageEvent: PageEvent) {}

    searchByName(event: Event) {
    }

}
