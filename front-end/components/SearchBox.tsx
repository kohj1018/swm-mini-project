import styles from '../styles/navBar.module.css';
import {useRouter} from "next/router";

const SearchBox = () => {
    const router = useRouter();
    return (
        <input 
            type="text"
            placeholder="검색"
            className={styles.searchBox}
        />
    );
}

export default SearchBox;