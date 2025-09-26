import { Address } from "./address";
import { PersonType } from "./person-type";

export interface Person{
    name: string,
    type: PersonType,
    document: string,
    phone: string,
    password: string,
    email: string,
    active: boolean,
    address: Address
}