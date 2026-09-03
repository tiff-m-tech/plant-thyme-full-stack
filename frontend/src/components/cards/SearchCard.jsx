import { altFromFileName } from "../../utils/altFromFileName";
import { useNavigate } from "react-router";
import Button from "../ui/Button";

export default function SearchCard({ imgPath, name, plant, addPlantToCollection }) {
    const navigate = useNavigate();

    return (
        <div className="search-card">
            <img
                src={`${import.meta.env.BASE_URL}images/plants/${imgPath}`}
                alt={altFromFileName(imgPath)}
                className="search-card-picture"
            />
            <div className="search-card-right-container">
                <h3 className="search-card-title">{name}</h3>
                <Button
                    innerText="Add Plant"
                    onClick={() => {
                        addPlantToCollection(plant);
                        navigate("/currentCollection");
                    }}
                />
            </div>
        </div>
    );
}
