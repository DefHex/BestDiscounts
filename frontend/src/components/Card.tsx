type CardProps={
    id: string,
    image: string,
    name: string,
    price: string,
    provider: string,
}
export default function Card(props:Readonly<CardProps>) {
    return (
        <div className={"card-info"}>
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