import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export default function Button({ innerText, onClick, icon, className = "", type = "button" }) {
    return (
        <button type={type} onClick={onClick} className={className}>
            {icon && <FontAwesomeIcon icon={icon} />}
            {innerText}
        </button>
    );
}
