import Card from "./Card.tsx";
import axios from "axios";
import type {userInfoType} from "../type/UserInfoType.ts";
import { MdRemoveShoppingCart } from "react-icons/md";

type UserPrefCardProps={
    id: string,
    image: string,
    name: string,
    price: string,
    provider: string,
    user:userInfoType
    shoppingCart:string[]
    setShoppingCart:(shoppingCart:string[])=>void
}
export default function UserPrefCard(props:Readonly<UserPrefCardProps>) {
    function removeFromCart(removedElementForPreRender:string) {
        props.setShoppingCart(props.shoppingCart.filter(id => id !== removedElementForPreRender));
        // console.table([props.user.id,props.id]);
        axios.post("/api/changeCart",{
            userId: props.user.id,
            productId: props.id,
            addTrueFalseDelete:false
        })
            .then(response => {
                console.log(response.data);
                props.setShoppingCart(response.data);
            }).catch(e => {
                console.error(e);
                props.setShoppingCart([... props.shoppingCart,removedElementForPreRender]);
            })
        }
    return (
        <div className={"card"}>
            <Card id={props.id} image={props.image} name={props.name} price={props.price} provider={props.provider}/>
            <button className={"removeFromCart"} onClick={()=>removeFromCart(props.id)}><MdRemoveShoppingCart /> Remove from cart</button>
        </div>
    );
}