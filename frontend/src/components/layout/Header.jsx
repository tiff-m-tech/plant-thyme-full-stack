import NavBar from "./NavBar";
import { Link } from "react-router";

export default function Header({ isLoggedIn, setIsLoggedIn }) {
    return (
        <header>
            <div className="header-left">
                <Link to="/home" className="header-brand-link">
                    <img
                        src={`${import.meta.env.BASE_URL}images/brand/mini-logo.png`}
                        alt=""
                        className="mini-logo"
                    />
                    <span className="header-brand-name">Plant Thyme</span>
                </Link>
            </div>
            <NavBar isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />
        </header>
    );
}
