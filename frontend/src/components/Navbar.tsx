import type {userInfoType} from "../type/UserInfoType.ts";
import type {DiscountInfoType} from "../type/DiscountInfoType.ts";
import {useEffect, useState} from "react";
import SearchBar from "./SearchBar.tsx";
import {useNavigate} from "react-router-dom";
import { MdLogout } from "react-icons/md";


interface NavbarProps {
    user: userInfoType | null,
    setUser: (user: userInfoType | null) => void,
    discounts: DiscountInfoType[],
    setDiscounts: (discounts: DiscountInfoType[]) => void,
    setFilteredDiscounts:(filteredDiscounts: DiscountInfoType[]) => void
    shoppingCart:string[]
}


export function Navbar({user, setUser, discounts, setFilteredDiscounts,shoppingCart}: NavbarProps) {
    const nav=useNavigate();
    //scroll animation
    const [prevScrollPosition, setPrevScrollPosition] = useState(0);
    const [visible, setVisible] = useState(true);
    useEffect(() => {
        function handleScroll() {
            const currentScrollPosition = window.pageYOffset;
            setVisible(prevScrollPosition > currentScrollPosition || currentScrollPosition < 10);
            setPrevScrollPosition(currentScrollPosition);
        }

        window.addEventListener("scroll", handleScroll);
        return () => window.removeEventListener("scroll", handleScroll);
    }, [prevScrollPosition]);

    function logout() {
        const host: string = window.location.host === "localhost:5173" ?
            "http://localhost:8080" :
            window.location.origin;
        window.open(host + "/logout", "_self");
        setUser(null)
    }

    return (
        <div>
            <nav style={{top: visible ? '0' : '-100px'}}>
                {/*{user && <img src={user.avatarUrl} alt={user.userName} className={"navbar-avatar"} onClick={()=>(nav("/userPref"))}/>}*/}
                {user &&
                    <div onClick={() => (nav("/userPref"))} className={"navbar-avatar-comp"}>
                        <img src={user.avatarUrl} alt={user.userName} className={"navbar-avatar"}/>
                        {shoppingCart.length > 0 &&
                            <span className={"item-number"}>
                                {shoppingCart.length}
                            </span>
                        }
                    </div>
                }
                <SearchBar discounts={discounts} setFilteredDiscounts={setFilteredDiscounts} />
                {user && (<button onClick={logout}><MdLogout /></button>)}
            </nav>
        </div>
    );
}