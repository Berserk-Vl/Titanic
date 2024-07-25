import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PassengerBatch } from './passengerbatch';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class PassengerService {
    private parameterAdded: boolean = false;
    parameterNames = ['offset', 'limit', 'name', 'sort', 'survived', 'age', 'sex', 'relatives'];

    constructor(private _httpClient: HttpClient) {}

    getPassengers(queryParameters: Map<string, string>): Observable<PassengerBatch> {
        let baseAddress = 'http://localhost:8080/passengers';
        if (queryParameters.size == 0) {
            return this._httpClient.get<PassengerBatch>(baseAddress);
        } else {
            baseAddress += '?';
        }
        this.parameterNames.forEach((parameterName) => {
            baseAddress += this.addParameter(queryParameters, parameterName);
        });
        this.parameterAdded = false;
        return this._httpClient.get<PassengerBatch>(baseAddress);
    }

    private addParameter(queryParameters: Map<string, string>, parameter: string): string {
        if (queryParameters.has(parameter) && (queryParameters.get(parameter) || '').length > 0) {
            let and = '';
            if (this.parameterAdded) {
                and = '&';
            } else {
                this.parameterAdded = true;
            }
            return `${and}${parameter}=${queryParameters.get(parameter)}`;
        }
        return '';
    }
}
