import type {DiscountInfoType} from "../type/DiscountInfoType.ts";
import { FaSearch } from "react-icons/fa";
type SearchBarProps = {
    discounts: DiscountInfoType[],
    setFilteredDiscounts: (filteredDiscounts: DiscountInfoType[]) => void
}
export default function SearchBar({discounts, setFilteredDiscounts}: Readonly<SearchBarProps>) {
        function filterDiscounts(e:string){
        const searchArray: DiscountInfoType[] = [];
        if(e.trim().length>0){
            discounts.forEach((discount:DiscountInfoType)=>(discount.name.toLowerCase().includes(e.toLowerCase()) && searchArray.push(discount)));
            setFilteredDiscounts(searchArray);
        }else{
            setFilteredDiscounts(discounts);
        }

    }
    return (
        <div className={"search-bar"}>
            {discounts.length > 0 && (
                <>
                    <FaSearch className={"search-icon"}/>
                    <input type={"text"}  placeholder={"  Search discounts..."} className={"search-input"}
                           id={"search"} name={"search"} onChange={e =>filterDiscounts(e.target.value) }/>
                </>
            )}
        </div>
    );
}