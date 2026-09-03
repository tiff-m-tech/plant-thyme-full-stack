import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import Button from "./Button";

export default function Modal({
    icon,
    isOpen,
    onClose,
    onConfirm,
    confirmText,
    cancelText,
    message,
    iconClassName = "",
    confirmButtonClassName = "",
}) {
    if (!isOpen) return null;

    return (
        <div className="modal-container">
            <div>
                <FontAwesomeIcon icon={icon} className={iconClassName} />
            </div>
            <div>{message}</div>
            <div className="modal-btns">
                <Button
                    innerText={confirmText}
                    onClick={onConfirm}
                    className={confirmButtonClassName}
                />
                <Button innerText={cancelText} onClick={onClose} />
            </div>
        </div>
    );
}
