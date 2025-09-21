import {IoArrowBack} from "react-icons/io5";
import type {userInfoType} from "../type/UserInfoType.ts";
import type {DiscountInfoType} from "../type/DiscountInfoType.ts";
import {useNavigate} from "react-router-dom";
import UserPrefCard from "./UserPrefCard.tsx";

type UserPrefProps = {
    user: userInfoType,
    discounts: DiscountInfoType[],
    shoppingCart: string[]
    setShoppingCart:(shoppingCart:string[])=>void
}

export default function UserPref({user, discounts, shoppingCart,setShoppingCart}: Readonly<UserPrefProps>) {
    const nav = useNavigate();
    const userSavedDiscounts = discounts.filter(discount => shoppingCart.includes(discount.id));

    return (
        <div>
            <IoArrowBack className={"back-button"} onClick={() => nav("/")}/>
            <div>
                <h1>Hello {user.userName}</h1>
                <img src={user.avatarUrl} alt={user.userName}/>
                <h2>Your saved discounts:</h2>
            </div>
            <div className={"card-grid"}>
                {userSavedDiscounts.length > 0 ?
                    userSavedDiscounts.map(
                        (discount: DiscountInfoType) => (
                            <UserPrefCard key={discount.id}
                                          id={discount.id}
                                          image={discount.image}
                                          name={discount.name}
                                          price={discount.price}
                                          provider={discount.provider}
                                          user={user}
                                          shoppingCart={shoppingCart}
                                          setShoppingCart={setShoppingCart}/>)) : <p>You have no saved discounts yet.</p>
                }
            </div>
        </div>
    );
}