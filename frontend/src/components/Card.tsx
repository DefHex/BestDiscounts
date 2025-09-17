import type {DiscountInfoType} from "../type/DiscountInfoType.ts";

export default function Card(props:Readonly<DiscountInfoType>) {
    return (
        <div className={"card"}>
            <div>
                <img className={"card-image"} src={props.image} alt={props.name}/>
                <h2>{props.name}</h2>
            </div>
            <div>
                <h2 className={"price"}>{props.price}</h2>
                <h4>{props.provider}</h4>
            </div>
        </div>
    );
}