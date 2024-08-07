import { useState } from 'react';
import { NavLink } from 'react-router-dom';

import LogoSVG from '@/assets/icons/logo.svg?react';
import HamburgerSVG from '@/assets/icons/hamburger.svg?react';
import GithubSVG from '@/assets/icons/github.svg?react';
import UserSVG from '@/assets/icons/user.svg?react';
import { Button } from '@/components/Button/Button';
import { Alarm } from '@/components/Alarm/Alarm';
import { HeaderSidebar } from '@/components/Header/HeaderSidebar';

import styles from './Header.module.scss';
import { baseURL } from '@/services/api';

export function Header() {
  const [isVisible, setIsVisible] = useState(false);
  // TODO : 사용자 로그인 상태 처리
  const isLoggedIn: boolean = false;

  const showSidebar = () => {
    setIsVisible(true);
  };

  const hideSidebar = () => {
    setIsVisible(false);
  };

  const handleLogin = () => {
    window.location.href = `${baseURL}/oauth2/authorization/github`;
  };

  return (
    <header className={styles.header}>
      <HeaderSidebar
        isVisible={isVisible}
        showSidebar={showSidebar}
        hideSidebar={hideSidebar}
      />
      <div className={`${styles.header_item} ${styles.left_item}`}>
        <button
          className={styles.button}
          onClick={() => setIsVisible((prev) => !prev)}
        >
          <HamburgerSVG width={30} height={30} />
        </button>
      </div>
      <div className={`${styles.header_item} ${styles.middle_item}`}>
        <NavLink to="/">
          <LogoSVG width={148} height={70} />
        </NavLink>
      </div>
      {isLoggedIn ? (
        <div className={`${styles.profile} ${styles.header_item}`}>
          <Alarm />
          <UserSVG width={40} height={40} />
        </div>
      ) : (
        <div className={`${styles.header_item} ${styles.right_item}`}>
          <Button color="black" fill={false} size="small" onClick={handleLogin}>
            <GithubSVG />
            <div className={styles.login}>시작하기</div>
          </Button>
        </div>
      )}
    </header>
  );
}
