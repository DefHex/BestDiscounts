import type {userInfoType} from "../type/UserInfoType.ts";
import Login from "./Login.tsx";
import type {DiscountInfoType} from "../type/DiscountInfoType.ts";
import DashbordCard from "./DashbordCard.tsx";

type DashboardProps = {
    user: userInfoType | null,
    discounts: DiscountInfoType[],
    filteredDiscounts: DiscountInfoType[],
    shoppingCart:string[],
    setShoppingCart:(shoppingCart:string[])=>void
}

export default function Dashboard({user, discounts, filteredDiscounts,shoppingCart,setShoppingCart}: Readonly<DashboardProps>) {

    return (
        <div>
            {user ?
                <div>
                    <h1>Welcome {user?.userName}</h1>
                    <img src={user?.avatarUrl} alt={user?.userName}/>
                    <h2>This weeks discounts:</h2>

                    <div className={"card-grid"}>
                        {filteredDiscounts.length > 0 ?
                            filteredDiscounts.map(
                                (discount: DiscountInfoType) => (
                                    <DashbordCard key={discount.id}
                                          id={discount.id}
                                          image={discount.image}
                                          name={discount.name}
                                          price={discount.price}
                                          provider={discount.provider}
                                          user={user}
                                          shoppingCart={shoppingCart}
                                          setShoppingCart={setShoppingCart}
                                    />)) :
                            discounts.length > 0 &&
                            discounts.map(
                                (discount: DiscountInfoType) => (
                                    <DashbordCard key={discount.id}
                                          id={discount.id}
                                          image={discount.image}
                                          name={discount.name}
                                          price={discount.price}
                                          provider={discount.provider}
                                          user={user}
                                          shoppingCart={shoppingCart}
                                          setShoppingCart={setShoppingCart}/>))
                        }
                    </div>
                </div> :
                (<Login/>)
            }
        </div>
    );
}