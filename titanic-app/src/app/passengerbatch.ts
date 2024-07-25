import { Passenger } from './passenger';
import { Total } from './total';

export interface PassengerBatch {
    passengers: Passenger[];
    total: Total;
}
