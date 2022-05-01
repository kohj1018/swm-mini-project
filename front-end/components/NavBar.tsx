import Image from 'next/image';
import Link from 'next/link';
import NavBarButtons from './NavBarButtons';
import SearchBox from './SearchBox';
import styles from '../styles/navBar.module.css';
import {useRouter} from "next/router";

const NavBar = () => {
  const router = useRouter();
  return (
    <nav className={styles.navBar}>
      <div className={styles.navBarContent}>
        <Link href="/">
          <a className= {styles.logo}>
            <Image
              width="164"
              height="34"
              className="inline-block" 
              src={require("../images/logo.png").default} 
              alt="밥은 먹고 다니니?"/>
          </a>
        </Link>
        <SearchBox/>
        <NavBarButtons/>
        {/* <SearchBox/> */}
      </div>
    </nav>
  )
}

export default NavBar