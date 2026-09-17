import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export default function Button({
    innerText,
    onClick,
    icon,
    className = "",
    type = "button",
    disabled = false,
}) {
    return (
        <button type={type} onClick={onClick} className={className} disabled={disabled}>
            {icon && <FontAwesomeIcon icon={icon} />}
            {innerText}
        </button>
    );
}
