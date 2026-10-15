import { useState } from "react";
import { useNavigate } from "react-router";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBug } from "@fortawesome/free-solid-svg-icons";
import { searchPlants } from "../../services/api";
import Button from "../ui/Button";
import SearchCard from "../cards/SearchCard";
import PageTitle from "../ui/PageTitle";
import SearchBar from "../ui/SearchBar";
import { usePageTitleForBrowserTab } from "../../hooks/usePageTitleForBrowserTab";

export default function AddPlant({ addPlantToCollection }) {
    const [searchValue, setSearchValue] = useState("");
    const [filteredPlants, setFilteredPlants] = useState([]);
    const [hasSearched, setHasSearched] = useState(false);
    const [searchError, setSearchError] = useState("");
    const navigate = useNavigate();

    function handleChange(event) {
        setSearchValue(event.target.value);
    }

    async function handleSearch() {
        if (searchValue.trim().length <= 1) return;

        try {
            const results = await searchPlants(searchValue.trim());
            setFilteredPlants(results);
            setHasSearched(true);
            setSearchError("");
        } catch (error) {
            console.error("Failed to search plants:", error);
            setSearchError("Unable to search right now. Please try again.");
        }
    }

    usePageTitleForBrowserTab("Add a Plant to Collection");

    return (
        <main id="addPlant">
            <Button
                innerText="Back"
                onClick={() => navigate(`/currentCollection`)}
                className="back-btn"
            />
            <PageTitle title="Plant Search" />
            <SearchBar
                value={searchValue}
                onChange={handleChange}
                onSearch={handleSearch}
                placeholder="Find your next leafy friend..."
            />
            <div className="search-cards-container">
                {filteredPlants.map((plant) => (
                    <SearchCard
                        key={plant.id}
                        imgPath={plant.imagePath}
                        name={plant.name}
                        plant={plant}
                        addPlantToCollection={addPlantToCollection}
                    />
                ))}
                {searchError && (
                    <p className="search-error-message">
                        <FontAwesomeIcon icon={faBug} /> {searchError}
                    </p>
                )}
                {hasSearched && !searchError && filteredPlants.length === 0 && (
                    <p className="no-search-results-found-message">
                        <FontAwesomeIcon icon={faBug} /> No results found, please search again!
                    </p>
                )}
            </div>
        </main>
    );
}
