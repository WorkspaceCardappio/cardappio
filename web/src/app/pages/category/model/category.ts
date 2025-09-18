import { UUID } from "crypto";

export interface Category {
    id: UUID,
    active: Boolean,
    image: String,
    subcategory: Category
}