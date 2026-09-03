import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFacebook, faTwitter, faInstagram } from "@fortawesome/free-brands-svg-icons";

export default function Footer() {
    return (
        <footer>
            <span>© {new Date().getFullYear()} Plant Thyme</span> |
            <div className="social-links">
                {/*} "noopener" main security protection against the new page manipulating the page that opened it,
                    "noreferrer" also provides referrer privacy (destination site does not receive URL or page user came from)
                    and generally includes the protection provided by noopener in modern browsers */}
                <a href="https://facebook.com" target="_blank" rel="noopener noreferrer">
                    <FontAwesomeIcon icon={faFacebook} className="facebook-icon" />
                </a>
                <a href="https://twitter.com" target="_blank" rel="noopener noreferrer">
                    <FontAwesomeIcon icon={faTwitter} className="twitter-icon" />
                </a>
                <a href="https://instagram.com" target="_blank" rel="noopener noreferrer">
                    <FontAwesomeIcon icon={faInstagram} className="instagram-icon" />
                </a>
            </div>
        </footer>
    );
}
