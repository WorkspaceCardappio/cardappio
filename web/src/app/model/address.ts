import { City } from "./city";

export interface Address{
    id: string,
    street: string,
    zipCode: string,
    district: string,
    number: string,
    active: boolean,
    city: City,

}