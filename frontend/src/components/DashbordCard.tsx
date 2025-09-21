import Card from "./Card.tsx";
import axios from "axios";
import type {userInfoType} from "../type/UserInfoType.ts";
import { FaCheck } from "react-icons/fa";
import { MdAddShoppingCart } from "react-icons/md";
type DashbordCardProps={
    id: string,
    image: string,
    name: string,
    price: string,
    provider: string,
    user:userInfoType,
    shoppingCart:string[]
    setShoppingCart:(shoppingCart:string[])=>void
}
export default function DashbordCard(props:Readonly<DashbordCardProps>) {
    // const [cartItems,setCartItems]=useState<string[]>(props.user.shoppingCart);
    function postAddToCart (addedElementForPreRender:string) {
        // adding the element early to render the button faster
        props.setShoppingCart([...(props.shoppingCart || []),addedElementForPreRender]);
        // console.table([props.user.id,props.id]);
        axios.post("/api/changeCart",{
            userId: props.user.id,
            productId: props.id,
            addTrueFalseDelete:true
        })
        .then(response => {
            console.log(response.data);
            props.setShoppingCart(response.data);
        }).catch(e => {
            console.error(e);
            // setting the value back in case it fails
            props.setShoppingCart((props.shoppingCart || []).filter(id => id !== addedElementForPreRender));
        })
    }

    return (
        <div className={"card"}>
            <Card id={props.id} image={props.image} name={props.name} price={props.price} provider={props.provider}/>
            {props.shoppingCart!=null && props.shoppingCart.includes(props.id)?
                <button className={"addedToCart"}><FaCheck /> Added to cart</button>
                :
                <button onClick={() => postAddToCart(props.id)}><MdAddShoppingCart /> Add to cart</button>
            }
        </div>
    );
}