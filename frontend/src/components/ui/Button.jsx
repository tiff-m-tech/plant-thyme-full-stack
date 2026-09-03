export default function Button({ innerText, onClick, className = "", type = "button" }) {
    return (
        <button type={type} onClick={onClick} className={className}>
            {innerText}
        </button>
    );
}
