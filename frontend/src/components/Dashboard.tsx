import type {userInfoType} from "../type/UserInfoType.ts";
import Login from "./Login.tsx";
import type {DiscountInfoType} from "../type/DiscountInfoType.ts";
import Card from "./Card.tsx";

type DashboardProps = {
    user: userInfoType | null,
    discounts: DiscountInfoType[],
    filteredDiscounts: DiscountInfoType[]
}

export default function Dashboard({user,discounts,filteredDiscounts}: Readonly<DashboardProps>) {

    return (
        <div>
            {user ?
                <div>
                    <h1>Welcome {user?.userName}</h1>
                    <img src={user?.avatarUrl} alt={user?.userName}/>
                    <h2>This weeks discounts:</h2>

                    <div className={"card-grid"}>
                        {filteredDiscounts.length>0 ?
                            filteredDiscounts.map(
                                (discount: DiscountInfoType)=>(
                                    <Card key={discount.id}
                                          id={discount.id}
                                          image={discount.image}
                                          name={discount.name}
                                          price={discount.price}
                                          provider={discount.provider}/>)):
                            discounts.length>0&&
                            discounts.map(
                                (discount: DiscountInfoType)=>(
                                    <Card key={discount.id}
                                          id={discount.id}
                                          image={discount.image}
                                          name={discount.name}
                                          price={discount.price}
                                          provider={discount.provider}/>))
                        }
                    </div>
                </div> :
                (<Login/>)
            }
        </div>
    );
}