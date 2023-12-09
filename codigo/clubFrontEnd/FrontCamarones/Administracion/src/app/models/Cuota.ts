import { Socio } from "./Socio";

export class Cuota{
    id: number;
    mesCuota: string;
    fechaVencimiento: Date;
    pagada: boolean;
    precioCuota: number;
    socio: Socio
}

