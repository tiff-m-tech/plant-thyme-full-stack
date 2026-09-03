import { useState } from "react";
import { useNavigate } from "react-router";
import plantDatabase from "../../data/plantDatabase.json";
import Button from "../ui/Button";
import SearchCard from "../cards/SearchCard";
import PageTitle from "../ui/PageTitle";
import SearchBar from "../ui/SearchBar";
import { usePageTitleForBrowserTab } from "../../hooks/usePageTitleForBrowserTab";

export default function AddPlant({ addPlantToCollection }) {
    const [searchValue, setSearchValue] = useState("");
    const [filteredPlants, setFilteredPlants] = useState([]);
    const [hasSearched, setHasSearched] = useState(false);
    const navigate = useNavigate();

    function handleChange(event) {
        setSearchValue(event.target.value);
    }

    function handleSearch() {
        if (searchValue.trim().length <= 1) return;
        const results = plantDatabase.filter((plant) =>
            plant.name.toLowerCase().trim().includes(searchValue.toLowerCase().trim()),
        );
        setFilteredPlants(results);
        setHasSearched(true);
    }

    usePageTitleForBrowserTab("Add a Plant to Collection");

    return (
        <main id="addPlant">
            {/* Passing -1 to navigate returns to the previous page in browser history. */}
            <Button innerText="Back" onClick={() => navigate(-1)} className="back-btn" />
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
                        imgPath={plant.image}
                        name={plant.name}
                        plant={plant}
                        addPlantToCollection={addPlantToCollection}
                    />
                ))}
                {hasSearched && filteredPlants.length === 0 && (
                    <p className="no-search-results-found-message">
                        No results found, please search again!
                    </p>
                )}
            </div>
        </main>
    );
}
