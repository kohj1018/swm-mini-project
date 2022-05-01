import Image from 'next/image';
import Link from 'next/link';
import styles from '../styles/navBar.module.css';
import {useRouter} from "next/router";

const NavBarButtons = () => {
    const router = useRouter();
    const iconSize = 28;
    return (
        <ul className={styles.navBarButtons}>
            <li><Link href="/"><a><Image 
                src={require("../images/main.svg").default}
                alt="main"
                width={iconSize}
                height={iconSize}
            /></a></Link></li>
            <li className={styles.searchButton}><Link href="/"><a><Image 
                src={require("../images/search.svg").default}
                alt="serach"
                width={iconSize}
                height={iconSize}
            /></a></Link></li>
            <li><Link href="/write"><a><Image 
                src={require("../images/add.svg").default}
                alt="post"
                width={iconSize}
                height={iconSize}
            /></a></Link></li>
            <li><Link href="/"><a><Image 
                src={require("../images/leaderboard.svg").default}
                alt="ranking"
                width={iconSize}
                height={iconSize}
            /></a></Link></li>
            <li><Link href="/my-page"><a><Image 
                src={require("../images/profile.svg").default}
                alt="profile"
                width={iconSize}
                height={iconSize}
            /></a></Link></li>
        </ul>
    );
}

export default NavBarButtons;