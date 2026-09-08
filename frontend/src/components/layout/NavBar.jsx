import { Link } from "react-router";
import { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPhone, faHouse, faLeaf, faRightFromBracket } from "@fortawesome/free-solid-svg-icons";

export default function NavBar({ isLoggedIn, setIsLoggedIn }) {
    const [isOpen, setIsOpen] = useState(false);

    function handleLogout() {
        setIsLoggedIn(false);
    }

    return (
        isLoggedIn && (
            <nav>
                <button
                    className="hamburger"
                    onClick={() => setIsOpen(!isOpen)}
                    aria-label="Toggle Menu"
                >
                    ☰
                </button>
                {/* Invisible backdrop covers the non-navbar area; clicking it closes the menu. */}
                {isOpen && <div className="nav-backdrop" onClick={() => setIsOpen(false)}></div>}
                <div className={isOpen ? "nav-links open" : "nav-links"}>
                    <Link to="/home" onClick={() => setIsOpen(false)}>
                        <FontAwesomeIcon icon={faHouse} /> Home
                    </Link>
                    <Link to="/contact" onClick={() => setIsOpen(false)}>
                        <FontAwesomeIcon icon={faPhone} /> Contact
                    </Link>
                    <Link to="/currentCollection" onClick={() => setIsOpen(false)}>
                        <FontAwesomeIcon icon={faLeaf} /> My Leafy Collection
                    </Link>
                    <Link
                        to="/"
                        onClick={() => {
                            setIsOpen(false);
                            handleLogout();
                        }}
                    >
                        <FontAwesomeIcon icon={faRightFromBracket} /> Log Out
                    </Link>
                </div>
            </nav>
        )
    );
}
