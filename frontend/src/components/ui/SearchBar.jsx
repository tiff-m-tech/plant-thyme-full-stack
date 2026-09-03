import Button from "./Button";

export default function SearchBar({ value, onChange, onSearch, placeholder, showButton = true }) {
    return (
        <div className="search-bar">
            <input
                type="search"
                name="search"
                placeholder={placeholder}
                value={value}
                onChange={onChange}
            />
            {/* Some search bars filter as the user types, so the Search button can be hidden when it is not needed. */}
            {showButton && <Button innerText="Search" onClick={onSearch} />}
        </div>
    );
}
